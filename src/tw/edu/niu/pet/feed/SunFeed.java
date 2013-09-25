package tw.edu.niu.pet.feed;

import tw.edu.niu.pet.Pet;

public class SunFeed extends AbsPetFeed {
	@Override
	public void updatePetAttributes(Pet pet){
		pet.setIntimacy(pet.getIntimacy() + 7);
		pet.setPower(pet.getPower() + 403);
		pet.setMoney(pet.getMoney() - 2381);
	}
}