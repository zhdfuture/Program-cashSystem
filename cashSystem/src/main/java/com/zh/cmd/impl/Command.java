package com.zh.cmd.impl;

import com.zh.cmd.Subject;

import java.util.Scanner;

public interface Command {
    Scanner scan=new Scanner(System.in);
    void execute(Subject subject);
}
