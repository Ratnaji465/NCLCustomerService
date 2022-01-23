package com.ncl.nclcustomerservice.enums;


import com.ncl.nclcustomerservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suprasoft on 1/7/2018.
 */


public enum MenuEnums {


    DASHBOARD(1, "DASHBOARD", R.drawable.dash_board, null, new int[]{15});
   /* LINEMAPPING(2,"LINE MAPPING", R.drawable.ic_line, LineMappingActivity.class,new int[]{15}),
    HRXHR(3,"HR X HR", R.drawable.ic_ltt, Hr_X_HrActivity.class,new int[]{15}),
    LTT_TICKETS(4,"LTT TICKETS", R.drawable.ltt_icon, LTTTicketsActivity.class, new int[]{15}),
    LINE_NOMINATE(5,"LINE NOMINATE", R.drawable.ic_line, LineNominateMainActivity.class,new int[]{15}),

*/
    //LOGOUT(20,"LOGOUT", R.drawable.logout,null,new int[]{15,20});


    private final int menuId;
    private final String menuTitle;
    private final int menuDrawable;
    private final Class<?> intentClass;
    private final int[] roles;
    static List<MenuEnums> list = new ArrayList<>();
    static List<MenuEnums> menuListByRole = new ArrayList<>();


    MenuEnums(int menuId, String menuTitle, int menuDrawable, Class<?> intentClass, int[] roles) {
        this.menuId = menuId;
        this.menuTitle = menuTitle;
        this.menuDrawable = menuDrawable;
        this.intentClass = intentClass;
        this.roles = roles;

    }

    public int getMenuId() {
        return menuId;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public int getMenuDrawable() {
        return menuDrawable;
    }

    public Class<?> getIntentClass() {
        return intentClass;
    }

    public int[] getRoles() {
        return roles;
    }


    static {

        for (MenuEnums menuEnums : MenuEnums.values()) {

            list.add(menuEnums);

        }

    }


    public static List<MenuEnums> getList() {
        return list;
    }

    public static List<MenuEnums> getListByRole(int roleId) {
        menuListByRole.clear();
        for (MenuEnums menuEnums : MenuEnums.values()) {
            int[] roles = menuEnums.getRoles();
            for (int i = 0; i < roles.length; i++) {
                if (roles[i] == roleId) {
                    menuListByRole.add(menuEnums);

                }


            }
        }


        return menuListByRole;
    }


}
