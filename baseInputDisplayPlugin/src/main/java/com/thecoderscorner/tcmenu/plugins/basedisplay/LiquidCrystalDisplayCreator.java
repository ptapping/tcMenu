/*
 * Copyright (c)  2016-2019 https://www.thecoderscorner.com (Nutricherry LTD).
 * This product is licensed under an Apache license, see the LICENSE file in the top-level directory.
 *
 */

package com.thecoderscorner.tcmenu.plugins.basedisplay;

import com.thecoderscorner.menu.pluginapi.AbstractCodeCreator;
import com.thecoderscorner.menu.pluginapi.CreatorProperty;
import com.thecoderscorner.menu.pluginapi.model.CodeVariableBuilder;
import com.thecoderscorner.menu.pluginapi.model.FunctionCallBuilder;
import com.thecoderscorner.menu.pluginapi.model.HeaderDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.thecoderscorner.menu.pluginapi.CreatorProperty.PropType.VARIABLE;
import static com.thecoderscorner.menu.pluginapi.SubSystem.DISPLAY;
import static com.thecoderscorner.menu.pluginapi.validation.CannedPropertyValidators.*;

public class LiquidCrystalDisplayCreator extends AbstractCodeCreator {

    private List<CreatorProperty> creatorProperties = new ArrayList<>(Arrays.asList(
            new CreatorProperty("LCD_RS", "RS connection to display", "1", DISPLAY, pinValidator()),
            new CreatorProperty("LCD_EN", "EN connection to display", "2", DISPLAY, pinValidator()),
            new CreatorProperty("LCD_D4", "D4 connection to display", "4", DISPLAY, pinValidator()),
            new CreatorProperty("LCD_D5", "D5 connection to display", "5", DISPLAY, pinValidator()),
            new CreatorProperty("LCD_D6", "D6 connection to display", "6", DISPLAY, pinValidator()),
            new CreatorProperty("LCD_D7", "D7 connection to display", "7", DISPLAY, pinValidator()),
            new CreatorProperty("LCD_WIDTH", "Number of chars across", "20", DISPLAY,
                                uintValidator(20)),
            new CreatorProperty("LCD_HEIGHT", "Number of chars down", "4", DISPLAY,
                                uintValidator(4)),
            new CreatorProperty("LCD_BACKLIGHT", "Controls the backlight (-1 no backlight)", "-1",
                                DISPLAY, pinValidator()),
            new CreatorProperty("LCD_PWM_PIN", "Advanced: PWM control contrast (-1 off)", "-1",
                                DISPLAY, optPinValidator()),
            new CreatorProperty("LCD_IO_DEVICE", "Advanced: IoAbstractionRef for custom i2c (default blank)",
                                "", DISPLAY, VARIABLE, variableValidator()))
    );

    @Override
    public void initCreator(String root) {
        addVariable(new CodeVariableBuilder().variableName("lcd").variableType("LiquidCrystal")
                        .requiresHeader("LiquidCrystalIO.h", false)
                        .requiresHeader("tcMenuLiquidCrystal.h", true, HeaderDefinition.PRIORITY_MIN)
                        .exportNeeded().param("LCD_RS").param("LCD_EN")
                        .param("LCD_D4").param("LCD_D5").param("LCD_D6").param("LCD_D7"));

        addVariable(new CodeVariableBuilder().variableName("renderer").variableType("LiquidCrystalRenderer")
                                .requiresHeader("LiquidCrystalIO.h", false)
                                .exportNeeded().param("lcd").param("LCD_WIDTH").param("LCD_HEIGHT"));

        addFunctionCall(new FunctionCallBuilder().functionName("setIoAbstraction").objectName("lcd")
                .paramFromPropertyWithDefault("LCD_IO_DEVICE", "ioUsingArduino()"));

        addFunctionCall(new FunctionCallBuilder().functionName("begin").objectName("lcd")
                                .param("LCD_WIDTH").param("LCD_HEIGHT"));

        addLibraryFiles("renderers/liquidcrystal/tcMenuLiquidCrystal.cpp",
                        "renderers/liquidcrystal/tcMenuLiquidCrystal.h");

        if(findPropertyValueAsIntWithDefault("LCD_BACKLIGHT", -1) != -1) {
            addFunctionCall(new FunctionCallBuilder().functionName("configureBacklightPin").objectName("lcd")
                                    .param("LCD_BACKLIGHT"));
            addFunctionCall(new FunctionCallBuilder().functionName("backlight").objectName("lcd"));
        }

        if(findPropertyValueAsIntWithDefault("LCD_PWM_PIN", -1) != -1) {
            addFunctionCall(new FunctionCallBuilder().functionName("pinMode").param("LCD_PWM_PIN").param("OUTPUT"));
            addFunctionCall(new FunctionCallBuilder().functionName("analogWrite").param("LCD_PWM_PIN").param(10));
        }

        addExportVariableIfPresent("LCD_IO_DEVICE", "IoAbstractionRef");
    }


    @Override
    public List<CreatorProperty> properties() {
        return creatorProperties;
    }
}
