package tw.edu.niu.pet.feed;

import tw.edu.niu.pet.Pet;

public class WaterFeed extends AbsPetFeed {
	@Override
	public void updatePetAttributes(Pet pet){
		pet.setIntimacy(pet.getIntimacy() + 4);
		pet.setPower(pet.getPower() + 171);
		pet.setMoney(pet.getMoney() - 254);
	}
}
