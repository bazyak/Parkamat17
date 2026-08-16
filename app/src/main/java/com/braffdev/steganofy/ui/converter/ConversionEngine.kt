package com.braffdev.steganofy.ui.converter

import com.braffdev.steganofy.R

/**
 * Conversion engine adapted from UnitConverterUltimate by Phil Shadlyn.
 * All conversion factors sourced from that project.
 * Category and unit names are stored as string resource IDs for localisation.
 */
object ConversionEngine {

    data class ConvUnit(
        val id: Int,
        val nameResId: Int,
        val toBase: Double,
        val fromBase: Double
    )

    data class Category(
        val id: Int,
        val nameResId: Int,
        val units: List<ConvUnit>
    )

    const val CAT_TEMPERATURE = 2

    val categories: List<Category> = listOf(

        Category(0, R.string.cat_length, listOf(
            ConvUnit(0,  R.string.unit_kilometre,      1000.0,          0.001),
            ConvUnit(1,  R.string.unit_mile,           1609.344,         0.000621371192237),
            ConvUnit(2,  R.string.unit_metre,          1.0,              1.0),
            ConvUnit(3,  R.string.unit_centimetre,     0.01,             100.0),
            ConvUnit(4,  R.string.unit_millimetre,     0.001,            1000.0),
            ConvUnit(5,  R.string.unit_micrometre,     0.000001,         1000000.0),
            ConvUnit(6,  R.string.unit_nanometre,      1e-9,             1e9),
            ConvUnit(7,  R.string.unit_yard,           0.9144,           1.09361329833771),
            ConvUnit(8,  R.string.unit_foot,           0.3048,           3.28083989501312),
            ConvUnit(9,  R.string.unit_inch,           0.0254,           39.3700787401575),
            ConvUnit(10, R.string.unit_nautical_mile,  1852.0,           0.000539956803455724),
            ConvUnit(11, R.string.unit_furlong,        201.168,          0.00497096953793),
        )),

        Category(1, R.string.cat_mass, listOf(
            ConvUnit(0, R.string.unit_kilogram,        1.0,           1.0),
            ConvUnit(1, R.string.unit_pound,           0.45359237,    2.20462262184878),
            ConvUnit(2, R.string.unit_gram,            0.001,         1000.0),
            ConvUnit(3, R.string.unit_milligram,       0.000001,      1000000.0),
            ConvUnit(4, R.string.unit_ounce,           0.028349523125,35.2739619495804),
            ConvUnit(5, R.string.unit_stone,           6.35029318,    0.157473044418),
            ConvUnit(6, R.string.unit_metric_ton,      1000.0,        0.001),
            ConvUnit(7, R.string.unit_short_ton,       907.18474,     0.00110231131092),
            ConvUnit(8, R.string.unit_long_ton,        1016.0469088,  0.000984206527611),
            ConvUnit(9, R.string.unit_grain,           0.00006479891, 15432.3583529414),
        )),

        Category(CAT_TEMPERATURE, R.string.cat_temperature, listOf(
            ConvUnit(0, R.string.unit_celsius,    0.0, 0.0),
            ConvUnit(1, R.string.unit_fahrenheit, 0.0, 0.0),
            ConvUnit(2, R.string.unit_kelvin,     0.0, 0.0),
            ConvUnit(3, R.string.unit_rankine,    0.0, 0.0),
            ConvUnit(4, R.string.unit_delisle,    0.0, 0.0),
            ConvUnit(5, R.string.unit_newton_t,   0.0, 0.0),
            ConvUnit(6, R.string.unit_reaumur,    0.0, 0.0),
        )),

        Category(3, R.string.cat_speed, listOf(
            ConvUnit(0, R.string.unit_kmh,   0.27777777777778,  3.6),
            ConvUnit(1, R.string.unit_mph,   0.44704,            2.2369362920544),
            ConvUnit(2, R.string.unit_ms,    1.0,                1.0),
            ConvUnit(3, R.string.unit_fts,   0.3048,             3.2808398950131),
            ConvUnit(4, R.string.unit_knot,  0.51444444444444,   1.9438444924406),
        )),

        Category(4, R.string.cat_area, listOf(
            ConvUnit(0, R.string.unit_sq_kilometre,  1000000.0,       0.000001),
            ConvUnit(1, R.string.unit_sq_metre,      1.0,              1.0),
            ConvUnit(2, R.string.unit_sq_centimetre, 0.0001,           10000.0),
            ConvUnit(3, R.string.unit_hectare,       10000.0,          0.0001),
            ConvUnit(4, R.string.unit_sq_mile,       2589988.110336,   3.86102158542e-7),
            ConvUnit(5, R.string.unit_sq_yard,       0.83612736,       1.19599004630108),
            ConvUnit(6, R.string.unit_sq_foot,       0.09290304,       10.7639104167097),
            ConvUnit(7, R.string.unit_sq_inch,       0.00064516,       1550.0031000062),
            ConvUnit(8, R.string.unit_acre,          4046.8564224,     0.000247105381467),
        )),

        Category(5, R.string.cat_volume, listOf(
            ConvUnit(0,  R.string.unit_litre,       0.001,              1000.0),
            ConvUnit(1,  R.string.unit_millilitre,  0.000001,           1000000.0),
            ConvUnit(2,  R.string.unit_cubic_metre, 1.0,                1.0),
            ConvUnit(3,  R.string.unit_cubic_cm,    0.000001,           1000000.0),
            ConvUnit(4,  R.string.unit_cubic_inch,  0.000016387064,     61023.7440947),
            ConvUnit(5,  R.string.unit_cubic_foot,  0.028316846592,     35.3146667215),
            ConvUnit(6,  R.string.unit_gallon_us,   0.003785411784,     264.172052358),
            ConvUnit(7,  R.string.unit_gallon_uk,   0.00454609,         219.969248299),
            ConvUnit(8,  R.string.unit_quart_us,    0.000946352946,     1056.68820943),
            ConvUnit(9,  R.string.unit_pint_us,     0.000473176473,     2113.37641887),
            ConvUnit(10, R.string.unit_cup_us,      0.0002365882365,    4226.75283773),
            ConvUnit(11, R.string.unit_fl_oz_us,    0.0000295735295625, 33814.0227018),
            ConvUnit(12, R.string.unit_barrel_us,   0.119240471196,     8.38641436058),
        )),

        Category(6, R.string.cat_time, listOf(
            ConvUnit(0, R.string.unit_year,        31536000.0,   3.17097919837646e-8),
            ConvUnit(1, R.string.unit_month,       2628000.0,    3.80517500e-7),
            ConvUnit(2, R.string.unit_week,        604800.0,     1.65343915343915e-6),
            ConvUnit(3, R.string.unit_day,         86400.0,      1.15740740740741e-5),
            ConvUnit(4, R.string.unit_hour,        3600.0,       0.000277777777777778),
            ConvUnit(5, R.string.unit_minute,      60.0,         0.0166666666666667),
            ConvUnit(6, R.string.unit_second,      1.0,          1.0),
            ConvUnit(7, R.string.unit_millisecond, 0.001,        1000.0),
            ConvUnit(8, R.string.unit_nanosecond,  1e-9,         1e9),
        )),

        Category(7, R.string.cat_energy, listOf(
            ConvUnit(0, R.string.unit_joule,       1.0,          1.0),
            ConvUnit(1, R.string.unit_kilojoule,   1000.0,       0.001),
            ConvUnit(2, R.string.unit_calorie,     4.184,        0.239005736137667),
            ConvUnit(3, R.string.unit_kilocalorie, 4184.0,       2.39005736137667e-4),
            ConvUnit(4, R.string.unit_kwh,         3600000.0,    2.77777777777778e-7),
            ConvUnit(5, R.string.unit_btu,         1055.05585262,9.47817120313317e-4),
            ConvUnit(6, R.string.unit_ft_lbf,      1.3558179483, 0.737562149457546),
        )),

        Category(8, R.string.cat_pressure, listOf(
            ConvUnit(0, R.string.unit_pascal,      1.0,         1.0),
            ConvUnit(1, R.string.unit_kilopascal,  1000.0,      0.001),
            ConvUnit(2, R.string.unit_megapascal,  1000000.0,   1e-6),
            ConvUnit(3, R.string.unit_bar,         100000.0,    0.00001),
            ConvUnit(4, R.string.unit_psi,         6894.75729,  1.45037738e-4),
            ConvUnit(5, R.string.unit_atmosphere,  101325.0,    9.86923267e-6),
            ConvUnit(6, R.string.unit_mmhg,        133.322387,  7.50061576e-3),
            ConvUnit(7, R.string.unit_torr,        133.3223684, 7.50061683e-3),
        )),

        Category(9, R.string.cat_power, listOf(
            ConvUnit(0, R.string.unit_watt,      1.0,         1.0),
            ConvUnit(1, R.string.unit_kilowatt,  1000.0,      0.001),
            ConvUnit(2, R.string.unit_megawatt,  1000000.0,   1e-6),
            ConvUnit(3, R.string.unit_hp_metric, 735.49875,   1.35962162e-3),
            ConvUnit(4, R.string.unit_hp_uk,     745.69987158,1.34102209e-3),
            ConvUnit(5, R.string.unit_btu_s,     1055.05585,  9.47817120e-4),
        )),

        Category(10, R.string.cat_data, listOf(
            ConvUnit(0, R.string.unit_bit,      1.19209290e-7, 8388608.0),
            ConvUnit(1, R.string.unit_byte,     9.53674316e-7, 1048576.0),
            ConvUnit(2, R.string.unit_kilobit,  1.220703125e-4,8192.0),
            ConvUnit(3, R.string.unit_kilobyte, 9.765625e-4,   1024.0),
            ConvUnit(4, R.string.unit_megabit,  0.125,          8.0),
            ConvUnit(5, R.string.unit_megabyte, 1.0,            1.0),
            ConvUnit(6, R.string.unit_gigabit,  128.0,          0.0078125),
            ConvUnit(7, R.string.unit_gigabyte, 1024.0,         9.765625e-4),
            ConvUnit(8, R.string.unit_terabit,  131072.0,       7.62939453e-6),
            ConvUnit(9, R.string.unit_terabyte, 1048576.0,      9.53674316e-7),
        )),

        Category(11, R.string.cat_cooking, listOf(
            ConvUnit(0,  R.string.unit_teaspoon_us,   4.9289215938e-6,  202884.136),
            ConvUnit(1,  R.string.unit_tablespoon_us, 1.47867647812e-5, 67628.045),
            ConvUnit(2,  R.string.unit_cup_us,        2.365882365e-4,   4226.753),
            ConvUnit(3,  R.string.unit_fl_oz_us,      2.95735295625e-5, 33814.023),
            ConvUnit(4,  R.string.unit_fl_oz_uk,      2.84130625e-5,    35195.080),
            ConvUnit(5,  R.string.unit_pint_us,       4.73176473e-4,    2113.376),
            ConvUnit(6,  R.string.unit_pint_uk,       5.6826125e-4,     1759.754),
            ConvUnit(7,  R.string.unit_quart_us,      9.46352946e-4,    1056.688),
            ConvUnit(8,  R.string.unit_gallon_us,     3.785411784e-3,   264.172),
            ConvUnit(9,  R.string.unit_gallon_uk,     4.54609e-3,       219.969),
            ConvUnit(10, R.string.unit_millilitre,    1e-6,             1000000.0),
            ConvUnit(11, R.string.unit_litre,         1e-3,             1000.0),
        )),

        Category(12, R.string.cat_torque, listOf(
            ConvUnit(0, R.string.unit_newton_metre, 1.0,            1.0),
            ConvUnit(1, R.string.unit_ft_lbf,       1.3558179483,   0.737562149458),
            ConvUnit(2, R.string.unit_in_lbf,       0.1129848290,   8.85074579349),
        )),
    )

    fun convert(value: Double, fromUnit: ConvUnit, toUnit: ConvUnit, categoryId: Int): Double {
        if (fromUnit.id == toUnit.id) return value
        return if (categoryId == CAT_TEMPERATURE) {
            convertTemperature(value, fromUnit.id, toUnit.id)
        } else {
            value * fromUnit.toBase * toUnit.fromBase
        }
    }

    private fun convertTemperature(value: Double, fromId: Int, toId: Int): Double {
        val celsius = when (fromId) {
            0 -> value
            1 -> (value - 32.0) * 5.0 / 9.0
            2 -> value - 273.15
            3 -> (value - 491.67) * 5.0 / 9.0
            4 -> 100.0 - value * 2.0 / 3.0
            5 -> value * 100.0 / 33.0
            6 -> value * 5.0 / 4.0
            else -> value
        }
        return when (toId) {
            0 -> celsius
            1 -> celsius * 9.0 / 5.0 + 32.0
            2 -> celsius + 273.15
            3 -> (celsius + 273.15) * 9.0 / 5.0
            4 -> (100.0 - celsius) * 1.5
            5 -> celsius * 33.0 / 100.0
            6 -> celsius * 4.0 / 5.0
            else -> celsius
        }
    }

    fun formatResult(value: Double): String {
        if (value.isNaN() || value.isInfinite()) return "—"
        val abs = Math.abs(value)
        return when {
            value == 0.0 -> "0"
            abs >= 1e12 || (abs < 1e-6 && abs > 0.0) -> String.format("%.6e", value)
            abs >= 1.0 -> String.format("%.8f", value).trimEnd('0').trimEnd('.')
            else -> String.format("%.10f", value).trimEnd('0').trimEnd('.')
        }
    }
}
