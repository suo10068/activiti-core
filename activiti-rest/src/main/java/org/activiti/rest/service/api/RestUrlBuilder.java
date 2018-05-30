
package org.activiti.rest.service.api;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Helper class for building URLs based on a base URL.
 * <p>
 * An instance can be created by using {@link #fromRequest(HttpServletRequest)} and {@link #fromCurrentRequest()}
 * which extracts the base URL from the request or by specifying the base URL through {@link #usingBaseUrl(String)}
 * <p>
 * {@link #buildUrl(String[], Object...)} can be called several times to build URLs based on the base URL
 *
 * @author Bassam Al-Sarori
 */
public class RestUrlBuilder {

    protected String baseUrl = "";

    protected RestUrlBuilder() {
    }

    protected RestUrlBuilder(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String buildUrl(String[] fragments, Object... arguments) {
        return new StringBuilder(baseUrl)
                .append("/")
                .append(MessageFormat.format(StringUtils.join(fragments, '/'), arguments))
                .toString();
    }

    /**
     * Uses baseUrl as the base URL
     */
    public static RestUrlBuilder usingBaseUrl(String baseUrl) {
        if (baseUrl == null) throw new ActivitiIllegalArgumentException("baseUrl can not be null");
        if (baseUrl.endsWith("/")) baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        return new RestUrlBuilder(baseUrl);
    }

    /**
     * Extracts the base URL from the request
     */
    public static RestUrlBuilder fromRequest(HttpServletRequest request) {
        return usingBaseUrl(ServletUriComponentsBuilder.fromServletMapping(request).build().toUriString());
    }

    /**
     * Extracts the base URL from current request
     */
    public static RestUrlBuilder fromCurrentRequest() {
        return usingBaseUrl(ServletUriComponentsBuilder.fromCurrentServletMapping().build().toUriString());
    }
}