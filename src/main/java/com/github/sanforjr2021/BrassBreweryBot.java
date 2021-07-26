package com.github.sanforjr2021;

import com.github.sanforjr2021.dao.DaoController;
import com.github.sanforjr2021.util.ConfigController;

import java.util.Properties;

public class BrassBreweryBot {

    public static final String VERSION = "2.0";

    public static void main(String args[]){
        ConfigController configController = new ConfigController();
        new DaoController(configController.getProperty("host"),
                configController.getProperty("database"),
                configController.getProperty("username"),
                configController.getProperty("password"));
    }
}
