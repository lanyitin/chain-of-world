package tw.edu.niu.pet.feed;

import tw.edu.niu.pet.Pet;

public class WindFeed extends AbsPetFeed {
	@Override
	public void updatePetAttributes(Pet pet){
		pet.setIntimacy(pet.getIntimacy() + 5);
		pet.setPower(pet.getPower() + 403);
		pet.setMoney(pet.getMoney() - 201);
	}
}
