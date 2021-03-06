package com.callegasdev.services;

import com.callegasdev.consumer.PetGuiceManager;
import com.callegasdev.injector.AppInjector;
import com.callegasdev.pet.Pet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.Test;

public class CutHairTest {

    Injector injector = Guice.createInjector(new AppInjector());
    PetGuiceManager appTest = injector.getInstance(PetGuiceManager.class);

    @Test
    public void doBathRegisteredPet(){
        appTest.registerPet(new Pet("Fish", "Babel", 30));
        Assert.assertTrue(appTest.executeCutHair(1,"long"));
    }

    @Test
    public void doBathNotRegisteredPet(){
        Assert.assertFalse(appTest.executeCutHair(1,"long"));
    }
}
