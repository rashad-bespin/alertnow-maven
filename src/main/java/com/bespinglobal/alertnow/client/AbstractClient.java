package com.bespinglobal.alertnow.client;

import com.bespinglobal.alertnow.config.Options;
import com.bespinglobal.alertnow.client.exception.ClientException;
import com.bespinglobal.alertnow.client.exception.IntegrationException;
import com.bespinglobal.alertnow.client.exception.NetworkException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
public abstract class AbstractClient {
    private final Options options;

    protected AbstractClient(@NotNull Options options) {
        this.options = Objects.requireNonNull(options, "Options object is required!");
    }

    protected ClientResponse doPost(@NotNull String parameter, @NotNull String payload) throws ClientException {
        return sendRequest(this.createRequestWithBody(parameter, payload, HttpPost::new));
    }

    private <T extends HttpRequestBase> T createRequest(String parameter, Function<String, T> requestFactory) throws ClientException {
        T request = requestFactory.apply(generateUrl(parameter));
        log.debug("METHOD: {}, parameters: \"{}\"", request.getMethod(), parameter);
        request.setConfig(RequestConfig.custom()
                .setConnectTimeout(options.getConnectionTimeout())
                .setConnectionRequestTimeout(options.getConnectionTimeout())
                .setSocketTimeout(options.getResponseTimeout())
                .build());
        request.setHeader("Accept-Language", options.getLanguage());
        request.setHeader("User-Agent", options.getUserAgent());
        request.setHeader("Authorization", String.format("X-API-KEY %s", options.getApiKey()));
        return request;
    }

    private <T extends HttpEntityEnclosingRequestBase> T createRequestWithBody(String parameter, String body, Function<String, T> requestFactory) throws ClientException {
        T request = createRequest(parameter, requestFactory);
        log.debug("Sending request...\n{}", body);
        request.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
        request.setHeader("Content-Type", "application/json");
        return request;
    }

    private String generateUrl(String parameter) throws ClientException {
        try {
            String url = (new URL(options.getHost() + "/" + parameter)).toURI().normalize().toString();
            log.debug("URL: \"{}\"", url);
            return url;
        } catch (URISyntaxException | MalformedURLException var3) {
            throw new ClientException("Invalid URL", var3);
        }
    }

    private ClientResponse sendRequest(HttpRequestBase request) throws ClientException {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().setDnsResolver(new TimeoutDnsResolver(options.getConnectionTimeout())).build();
            Throwable var3 = null;

            try {
                CloseableHttpResponse response = client.execute(request);
                Throwable var5 = null;

                try {
                    int code = response.getStatusLine().getStatusCode();
                    log.debug("Received HTTP response code {}", code);
                    Object entity;
                    if (code == 204) {
                        entity = new ClientResponse(ClientResponse.Status.EMPTY);
                        return (ClientResponse) entity;
                    } else {
                        entity = response.getEntity();
                        if (entity == null) {
                            throw new IntegrationException("Content expected");
                        } else {
                            String body = EntityUtils.toString((HttpEntity) entity, StandardCharsets.UTF_8);
                            log.debug("Received response\n{}", body);
                            ClientResponse var9;
                            if (code == 401 || code == 403) {
                                var9 = new ClientResponse(ClientResponse.Status.UNAUTHORIZED, body);
                                return var9;
                            } else if (code >= 400) {
                                var9 = new ClientResponse(ClientResponse.Status.ERROR, body);
                                return var9;
                            } else {
                                switch (code) {
                                    case 200:
                                    case 201:
                                    case 202:
                                        if (StringUtils.isNotBlank(body)) {
                                            var9 = new ClientResponse(ClientResponse.Status.MESSAGE, body);
                                            return var9;
                                        }

                                        var9 = new ClientResponse(ClientResponse.Status.EMPTY);
                                        return var9;
                                    default:
                                        throw new IntegrationException("Unexpected HTTP response code {}", new Object[]{code});
                                }
                            }
                        }
                    }
                } catch (Throwable var48) {
                    var5 = var48;
                    throw var48;
                } finally {
                    if (response != null) {
                        if (var5 != null) {
                            try {
                                response.close();
                            } catch (Throwable var47) {
                                var5.addSuppressed(var47);
                            }
                        } else {
                            response.close();
                        }
                    }

                }
            } catch (Throwable var50) {
                var3 = var50;
                throw var50;
            } finally {
                if (client != null) {
                    if (var3 != null) {
                        try {
                            client.close();
                        } catch (Throwable var46) {
                            var3.addSuppressed(var46);
                        }
                    } else {
                        client.close();
                    }
                }

            }
        } catch (UnsupportedCharsetException | ParseException var52) {
            throw new IntegrationException("Failed to read the message body", new Object[]{var52});
        } catch (IOException var53) {
            throw new NetworkException("Request failed", var53);
        }
    }
}