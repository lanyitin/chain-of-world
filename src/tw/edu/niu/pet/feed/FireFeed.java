package tw.edu.niu.pet.feed;

import tw.edu.niu.pet.Pet;

public class FireFeed extends AbsPetFeed {
	@Override
	public void updatePetAttributes(Pet pet){
		pet.setIntimacy(pet.getIntimacy() - 3);
		pet.setPower(pet.getPower() + 520);
		pet.setMoney(pet.getMoney() - 89);
	}
}
