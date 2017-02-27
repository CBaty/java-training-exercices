package io.robusta.birthday.implementations;

import java.util.ArrayList;
import java.util.List;

import io.robusta.birthday.interfaces.IGeneration;

/**
 * Created by Nicolas Zozol on 04/10/2016.
 */
public class Generation implements IGeneration {

	List<PeopleCollection> collections;

	public Generation() {

	}

	public Generation(int n, int collectionSize) {
		this.collections = createAllRandom(n, collectionSize);
	}

	@Override
	public PeopleCollection createRandom(int size) {
		PeopleCollection newRandom = new PeopleCollection(size);
		return newRandom;
	}

	@Override
	public List<PeopleCollection> createAllRandom(int n, int size) {
		List<PeopleCollection> listRandom = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			listRandom.add(this.createRandom(size));
		}
		return listRandom;
	}

	@Override
	public List<PeopleCollection> getPeopleCollections() {
		return collections;
	}

	@Override
	public int getNumberOfCollectionsThatHasTwoPeopleWithSameBirthday() {
		int number_CollectionsWithSameNumber = 0;
		for (PeopleCollection i : this.collections) {
			if (i.hasSame())
				number_CollectionsWithSameNumber++;
		}
		return number_CollectionsWithSameNumber;
	}

	@Override
    public boolean isLessThan50() {
		int half_size = this.getPeopleCollections().size()/2;
		if (this.getNumberOfCollectionsThatHasTwoPeopleWithSameBirthday() < 50){
			return true;
		}
    	return false;
    }

}
