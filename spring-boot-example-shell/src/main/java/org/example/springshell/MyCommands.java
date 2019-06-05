package org.example.springshell;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class MyCommands {

    @Autowired
    private HelloService helloService;

    @ShellMethod("Add two integers together.")
    public int add(int a, int b) {
        return a + b;
    }

    @ShellMethod("Display stuff.")
    public String echo(String arg1, String arg2, String arg3) {
        return String.format("You said: " + Arrays.asList(arg1, arg2, arg3));
    }

    @ShellMethod("Say Hello")
    public String hello(@ShellOption(defaultValue = "World") String name) {
        return helloService.hello(name);
    }

}
