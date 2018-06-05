package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class RootPMTest {

    @Test
    void testGetCantons(){
        //given
        RootPM rootpm = new RootPM();

        //when
        List<Canton> canton = rootpm.getCantons();

        //then
        Assertions.assertEquals(26, canton.size());
    }

}