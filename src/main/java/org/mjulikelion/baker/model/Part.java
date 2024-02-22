package org.mjulikelion.baker.model;

import static org.mjulikelion.baker.errorcode.ErrorCode.INVALID_PART_ERROR;

import org.mjulikelion.baker.exception.InvalidDataException;

public enum Part {
    SERVER, WEB;

    public static Part findBy(String part) {
        for (Part p : Part.values()) {
            if (p.name().equals(part)) {
                return p;
            }
        }
        throw new InvalidDataException(INVALID_PART_ERROR);
    }
}
