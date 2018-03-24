/*
 *        _____                     __    _     _   _____ _
 *       |   __|___ ___ _ _ ___ ___|  |  |_|___| |_|  _  | |_ _ ___
 *       |__   | -_|  _| | | -_|  _|  |__| |_ -|  _|   __| | | |_ -|
 *       |_____|___|_|  \_/|___|_| |_____|_|___|_| |__|  |_|___|___|
 *
 *  ServerListPlus - http://git.io/slp
 *  Copyright (c) 2014, Minecrell <https://github.com/Minecrell>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.hotmail.AdrianSR.ProMaintenanceMode.Utils;

import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class IntegerRange implements ConfigurationSerializable {
    private static final String SEPARATOR = "..";
    private static final Pattern SEPARATOR_PATTERN = Pattern.compile(SEPARATOR, Pattern.LITERAL);

    private final int from, to;

    public IntegerRange(int single) {
        this.from = this.to = single;
    }

    public IntegerRange(int from, int to) {
        if (from > to) throw new IllegalArgumentException("Invalid range: from " + from + " to " + to);
        this.from = from;
        this.to = to;
    }

    protected IntegerRange(IntegerRange other) {
        this(other.from, other.to);
    }

    public IntegerRange(String range) {
        this(parse(range));
    }

    public boolean isSingle() {
        return from == to;
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public static IntegerRange parse(String range) {
        try {
            return new IntegerRange(Integer.parseInt(range));
        } catch (NumberFormatException ignored) {}

        // Let's try to parse the range
        String[] parts = SEPARATOR_PATTERN.split(range, 2);
        if (parts.length != 2) throw new IllegalArgumentException("Invalid range: " + range);

        // Now parse both numbers
        return new IntegerRange(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    @Override
    public String toString() {
        return serialize().toString();
    }

    @Override
    public Object serialize() {
        return isSingle() ? from : from + SEPARATOR + to;
    }
}
