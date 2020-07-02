package com.fastcampus.javaallinone.project3.mycontact.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RenameNotPermittedException extends RuntimeException {
    private static final String MESSAGE = "Cannot change the name";

    public RenameNotPermittedException(){
        super(MESSAGE);
        log.error(MESSAGE);
    }
}
