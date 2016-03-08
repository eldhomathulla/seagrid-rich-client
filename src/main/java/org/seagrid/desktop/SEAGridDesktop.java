/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/
package org.seagrid.desktop;

import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.seagrid.desktop.ui.home.HomeWindow;
import org.seagrid.desktop.ui.login.LoginWindow;
import org.seagrid.desktop.util.SEAGridContext;
import org.seagrid.desktop.util.messaging.SEAGridEvent;
import org.seagrid.desktop.util.messaging.SEAGridEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SEAGridDesktop extends Application{
    private final static Logger logger = LoggerFactory.getLogger(SEAGridDesktop.class);

    public SEAGridDesktop(){
        SEAGridEventBus.getInstance().register(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginWindow loginWindow =  new LoginWindow();
        loginWindow.displayLoginAndWait();
        boolean isAuthenticated = SEAGridContext.getInstance().getAuthenticated();
        if(isAuthenticated){
            HomeWindow homeWindow =  new HomeWindow();
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            primaryStage.setX(bounds.getMinX());
            primaryStage.setY(bounds.getMinY());
            primaryStage.setWidth(bounds.getWidth());
            primaryStage.setHeight(bounds.getHeight());
            homeWindow.start(primaryStage);
            primaryStage.setOnCloseRequest(t -> {
                Platform.exit();
                System.exit(0);
            });
        }
    }

    @Subscribe
    public void handleSEAGridEvents(SEAGridEvent event){
        if(event.getEventType().equals(SEAGridEvent.SEAGridEventType.LOGOUT)){
            try {
                start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}