package com.xorprogramming.jcolor;

import java.awt.Color;
import java.util.Arrays;
import java.util.Comparator;

public enum NamedColor
{
    ALICE_BLUE("Alice Blue", 0XF0F8FF),
    ANTIQUE_WHITE("Antique White", 0XFAEBD7),
    AQUAMARINE("Aquamarine", 0X7FFFD4),
    AZURE("Azure", 0XF0FFFF),
    BEIGE("Beige", 0XF5F5DC),
    BISQUE("Bisque", 0XFFE4C4),
    BLACK("Black", 0X000000),
    BLANCHED_ALMOND("Blanched Almond", 0XFFEBCD),
    BLUE("Blue", 0X0000FF),
    BLUE_VIOLET("Blue Violet", 0X8A2BE2),
    BROWN("Brown", 0XA52A2A),
    BURLY_WOOD("Burly Wood", 0XDEB887),
    CADET_BLUE("Cadet Blue", 0X5F9EA0),
    CHARTREUSE("Chartreuse", 0X7FFF00),
    CHOCOLATE("Chocolate", 0XD2691E),
    CORAL("Coral", 0XFF7F50),
    CORNFLOWER_BLUE("Cornflower Blue", 0X6495ED),
    CORNSILK("Cornsilk", 0XFFF8DC),
    CRIMSON("Crimson", 0XDC143C),
    CYAN("Cyan", 0x00FFFF),
    DARK_BLUE("Dark Blue", 0X00008B),
    DARK_CYAN("Dark Cyan", 0X008B8B),
    DARK_GOLDENROD("Dark Goldenrod", 0XB8860B),
    DARK_GRAY("Dark Gray", 0xA9A9A9),
    DARK_GREEN("Dark Green", 0X006400),
    DARK_KHAKI("Dark Khaki", 0XBDB76B),
    DARK_MAGENTA("Dark Magenta", 0X8B008B),
    DARK_OLIVE_GREEN("Dark Olive Green", 0X556B2F),
    DARK_ORANGE("Dark Orange", 0XFF8C00),
    DARK_ORCHID("Dark Orchid", 0X9932CC),
    DARK_RED("Dark Red", 0X8B0000),
    DARK_SALMON("Dark Salmon", 0XE9967A),
    DARK_SEA_GREEN("Dark Sea Green", 0X8FBC8F),
    DARK_SLATE_BLUE("Dark Slate Blue", 0X483D8B),
    DARK_SLATE_GRAY("Dark Slate Gray", 0X2F4F4F),
    DARK_TURQUOISE("Dark Turquoise", 0X00CED1),
    DARK_VIOLET("Dark Violet", 0X9400D3),
    DEEP_PINK("Deep Pink", 0XFF1493),
    DEEP_SKY_BLUE("Deep Sky Blue", 0X00BFFF),
    DIM_GRAY("Dim Gray", 0x696969),
    DODGER_BLUE("Dooger Blue", 0X1E90FF),
    FIRE_BRICK("Fire Brick", 0XB22222),
    FLORAL_WHITE("Floral White", 0XFFFAF0),
    FOREST_GREEN("Forest Green", 0X228B22),
    GAINSBORO("Gainsboro", 0XDCDCDC),
    GHOST_WHITE("Ghost White", 0XF8F8FF),
    GOLD("Gold", 0XFFD700),
    GOLDENROD("Goldenrod", 0XDAA520),
    GRAY("Gray", 0X808080),
    GREEN("Green", 0X008000),
    GREEN_YELLOW("Green Yellow", 0XADFF2F),
    HONEYDEW("Honeydew", 0XF0FFF0),
    HOT_PINK("Hot Pink", 0XFF69B4),
    INDIAN_RED("Indian Red", 0XCD5C5C),
    INDIGO("Indigo", 0X4B0082),
    IVORY("Ivory", 0XFFFFF0),
    KHAKI("Khaki", 0XF0E68C),
    LAVENDER("Lavender", 0XE6E6FA),
    LAVENDER_BLUSH("Lavender Blush", 0XFFF0F5),
    LAWN_GREEN("Lawn Green", 0X7CFC00),
    LEMON_CHIFFON("Lemon Chiffon", 0XFFFACD),
    LIGHT_BLUE("Light Blue", 0XADD8E6),
    LIGHT_CORAL("Light Coral", 0XF08080),
    LIGHT_CYAN("Light Cyan", 0XE0FFFF),
    LIGHT_GOLDENROD_YELLOW("Light Goldenrow Yellow", 0XFAFAD2),
    LIGHT_GRAY("Light Gray", 0xD3D3D3),
    LIGHT_GREEN("Light Green", 0X90EE90),
    LIGHT_PINK("Light Pink", 0XFFB6C1),
    LIGHT_SALMON("Light Salmon", 0XFFA07A),
    LIGHT_SEA_GREEN("Light Sea Green", 0X20B2AA),
    LIGHT_SKY_BLUE("Light Sky Blue", 0X87CEFA),
    LIGHT_SLATE_GRAY("Light Slate Gray", 0X778899),
    LIGHT_STEEL_BLUE("Light Steel Blue", 0XB0C4DE),
    LIGHT_YELLOW("Light Yellow", 0XFFFFE0),
    LIME("Lime", 0X00FF00),
    LIME_GREEN("Lime Green", 0X32CD32),
    LINEN("Linen", 0XFAF0E6),
    MAGENTA("Magenta", 0XFF00FF),
    MAROON("Maroon", 0X800000),
    MEDIUM_AQUA_MARINE("Medium Aqua Marine", 0X66CDAA),
    MEDIUM_BLUE("Medium Blue", 0X0000CD),
    MEDIUM_ORCHID("Medium Orchid", 0XBA55D3),
    MEDIUM_PURPLE("Medium Purple", 0X9370DB),
    MEDIUM_SEA_GREEN("Medium Sea Green", 0X3CB371),
    MEDIUM_SLATE_BLUE("Medium Slate Blue", 0X7B68EE),
    MEDIUM_SPRING_GREEN("Medium Spring Green", 0X00FA9A),
    MEDIUM_TURQUOISE("Medium Turquoise", 0X48D1CC),
    MEDIUM_VIOLET_RED("Medium Violet Red", 0XC71585),
    MIDNIGHT_BLUE("Midnight Blue", 0X191970),
    MINT_CREAM("Mint Cream", 0XF5FFFA),
    MISTY_ROSE("Minty Rose", 0XFFE4E1),
    MOCCASIN("Moccasin", 0XFFE4B5),
    NAVAJO_WHITE("Navajo White", 0XFFDEAD),
    NAVY("Navy", 0X000080),
    OLD_LACE("Old Lace", 0XFDF5E6),
    OLIVE("Olive", 0X808000),
    OLIVE_DRAB("Olive Drab", 0X6B8E23),
    ORANGE("Orange", 0XFFA500),
    ORANGE_RED("Orange Red", 0XFF4500),
    ORCHID("Orchid", 0XDA70D6),
    PALE_GOLDENROD("Pale Goldenrod", 0XEEE8AA),
    PALE_GREEN("Pale Green", 0X98FB98),
    PALE_TURQUOISE("Pale Turquoise", 0XAFEEEE),
    PALE_VIOLET_RED("Pale Violet Red", 0XDB7093),
    PAPAYA_WHIP("Papaya Whip", 0XFFEFD5),
    PEACH_PUFF("Peach Puff", 0XFFDAB9),
    PERU("Peru", 0XCD853F),
    PINK("Pink", 0XFFC0CB),
    PLUM("Plum", 0XDDA0DD),
    POWDER_BLUE("Powder Blue", 0XB0E0E6),
    PURPLE("Purple", 0X800080),
    RED("Red", 0XFF0000),
    ROSY_BROWN("Rosy Brown", 0XBC8F8F),
    ROYAL_BLUE("Royal Blue", 0X4169E1),
    SADDLE_BROWN("Saddle Brown", 0X8B4513),
    SALMON("Salmon", 0XFA8072),
    SANDY_BROWN("Sandy Brown", 0XF4A460),
    SEA_GREEN("Sea Green", 0X2E8B57),
    SEA_SHELL("Sea Shell", 0XFFF5EE),
    SIENNA("Sienna", 0XA0522D),
    SILVER("Silver", 0XC0C0C0),
    SKY_BLUE("Sky Blue", 0X87CEEB),
    SLATE_BLUE("Slate Blue", 0X6A5ACD),
    SLATE_GRAY("Slate Gray", 0X708090),
    SNOW("Snow", 0XFFFAFA),
    SPRING_GREEN("Spring Green", 0X00FF7F),
    STEEL_BLUE("Steel Blue", 0X4682B4),
    TAN("Tan", 0XD2B48C),
    TEAL("Teal", 0X008080),
    THISTLE("Thistle", 0XD8BFD8),
    TOMATO("Tomato", 0XFF6347),
    TURQUOISE("Turquoise", 0X40E0D0),
    VIOLET("Violet", 0XEE82EE),
    WHEAT("Wheat", 0XF5DEB3),
    WHITE("White", 0xFFFFFF),
    WHITE_SMOKE("White Smoke", 0XF5F5F5),
    YELLOW("Yellow", 0XFFFF00),
    YELLOW_GREEN("Yellow Green", 0X9ACD32);

    private static final NamedColor[] SORTED_COLORS = NamedColor.values();

    static
    {
        Arrays.sort(SORTED_COLORS, new Comparator<NamedColor>()
        {

            @Override
            public int compare(NamedColor o1, NamedColor o2)
            {
                return Integer.compare(o1.color, o2.color);
            }

        });
    }


    public static NamedColor getMatch(Color c)
    {
        int r = (c.getRed() << 16) & 0x00FF0000;
        int g = (c.getGreen() << 8) & 0x0000FF00;
        int b = c.getBlue() & 0x000000FF;
        int color = r | g | b;
        return binarySearch(color, 0, SORTED_COLORS.length - 1);
    }


    private static NamedColor binarySearch(int color, int start, int end)
    {
        if (start > end)
        {
            return null;
        }
        int middleIndex = (start + end) / 2;
        NamedColor middleColor = SORTED_COLORS[middleIndex];
        if (middleColor.color > color)
        {
            return binarySearch(color, start, middleIndex - 1);
        }
        else if (middleColor.color < color)
        {
            return binarySearch(color, middleIndex + 1, end);
        }
        return middleColor;
    }

    private final String name;
    private final int    color;


    NamedColor(String name, int color)
    {
        this.name = name;
        this.color = color;
    }


    @Override
    public String toString()
    {
        return name;
    }
}
