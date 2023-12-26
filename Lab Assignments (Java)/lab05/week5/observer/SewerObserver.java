package week5.observer;

import week5.Sewer;

import java.util.ArrayList;
import java.util.HashMap;

/* Sewer Observer Interface */
public interface SewerObserver {

    /**
     * Update
     * Alter a maintainers to do list based on a sewer
     *
     * @param sewer, the sewer in need of attention
     */
    void update(Sewer sewer); //update a maintainers to do list based on a sewer
}
