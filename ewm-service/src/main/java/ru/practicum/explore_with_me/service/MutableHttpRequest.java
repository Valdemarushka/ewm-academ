package ru.practicum.explore_with_me.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MutableHttpRequest extends HttpServletRequestWrapper {

    private final Map<String, String[]> mutableParams = new HashMap<>();

    public MutableHttpRequest(final HttpServletRequest request) {
        super(request);
    }

    public void addParameter(String name, String value) {
        if (value != null)
            mutableParams.put(name, new String[]{value});

    }

    @Override
    public String getParameter(final String name) {
        String[] values = getParameterMap().get(name);

        return Arrays.stream(values)
                .findFirst()
                .orElse(super.getParameter(name));
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> allParameters = new HashMap<>();
        allParameters.putAll(super.getParameterMap());
        allParameters.putAll(mutableParams);

        return Collections.unmodifiableMap(allParameters);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(getParameterMap().keySet());
    }

    @Override
    public String[] getParameterValues(final String name) {
        return getParameterMap().get(name);
    }

    @Override
    public String getQueryString() {
        StringBuilder builder = new StringBuilder();
        for (String param : getParameterMap().keySet()) {
            builder.append(param).append("=")
                    .append(URLEncoder.encode(getParameter(param), StandardCharsets.UTF_8).replaceAll("\\+", "%20")).append("&");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }
}
