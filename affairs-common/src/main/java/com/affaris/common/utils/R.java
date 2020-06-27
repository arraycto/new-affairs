package com.affaris.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果
 *
 * @author Vulgarities
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 200);
        put("msg", "SUCCESS");
    }

    public static R success() {
        return new R();
    }

    public static R success(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R success(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R success(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R failed() {
        R r = new R();
        r.put("code", 500);
        r.put("msg", "FAILED");
        return r;
    }

    public static R failed(String msg) {
        R r = new R();
        r.put("code", 500);
        r.put("msg", msg);
        return r;
    }

    public static R failed(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R failed(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}