package com.earl.auth;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BreakTimer {

    public static void main(String[] args) {
        try{
            throw new Exception();
        }catch (Exception e){
            log.error("error", e);
        }
    }
}
