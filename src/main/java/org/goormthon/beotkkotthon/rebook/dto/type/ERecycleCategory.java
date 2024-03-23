package org.goormthon.beotkkotthon.rebook.dto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ERecycleCategory {
    PAPER("Paper", List.of("wood", "publication", "paper", "box", "carton", "cardboard", "paper product", "hardwood", "shipping box")),
    GLASS("Glass", List.of("glass bottle, glass")),
    CAN("Can", List.of("beverage can", "aluminum can", "tin can", "tin")),
    PLASTIC("Plastic", List.of("plastic bottle", "plastic")),
    CLOTHES("Clothing", List.of("sleeve", "headgear", "hat", "bag", "t-shirt", "shoe", "collar", "eyewear", "formal wear", "outerwear", "blazer", "hood", "sleeveless shirt", "shorts", "active pants", "pocket", "dress shirt", "jersey", "coat", "workwear", "jacket", "sports uniform", "cap", "sportswear", "clothing", "shoulder bag"));

    private final String recycleCategory;
    private final List<String> kindList;

    private static Boolean hasDescription(ERecycleCategory eRecycleCategory, String description) {
        return eRecycleCategory.getKindList().stream().anyMatch(kind -> kind.equals(description));
    }

    public static String getRecycleCategory(String description) {
        for (ERecycleCategory eRecycleCategory: ERecycleCategory.values()) {
            if (hasDescription(eRecycleCategory, description)) {
                return eRecycleCategory.getRecycleCategory();
            }
        }
        return null;
    }
}
