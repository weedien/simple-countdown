package cn.weedien.countdown.service;

public interface QueryCodeService {

    String genQueryCode();

    boolean isValidQueryCode(String code);
}
