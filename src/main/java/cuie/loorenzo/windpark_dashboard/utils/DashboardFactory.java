package cuie.loorenzo.windpark_dashboard.utils;

import cuie.loorenzo.windpark_dashboard.DashboardControl;

import java.util.Arrays;
import java.util.OptionalDouble;

public class DashboardFactory {

    public static DashboardControl defaultDashboard(String name, double prod2015, double prod2016, double prod2017, double prod2018, double powerVal) {
        double factor = 1.3;
        double maxProdVal = calcMaxProdVal(factor, prod2015, prod2016, prod2017, prod2018);
        double maxPowerVak = powerVal * factor;
        return new DashboardControl(name, prod2015, prod2016, prod2017, prod2018, maxProdVal, powerVal, maxPowerVak);
    }

    private static double calcMaxProdVal(double factor, double... prod) {
        OptionalDouble max = Arrays.stream(prod).max();
        if (max.isPresent()) {
            return max.getAsDouble() * factor;
        }
        throw new RuntimeException("prod list must not be empty");
    }

}
