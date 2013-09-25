package tw.edu.niu.pet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import tw.edu.niu.pet.feed.AbsPetFeed;

public class Pet {
	OnPetLevelUpListener levelUpListener;
	OnPetMoneyChangeListener moneyChangeListner;
	OnPetPowerChangeListener powerChangeListener;
	OnPetIntimacyChangeListener intimacyChangeListener;
	int level;
    int intimacy;
    int power;
    int money;
    Map<AbsPetFeed, Integer> feedTypeCountMap;
    
	public Pet() {
		super();
		level = 1;
		intimacy = 0;
		power = 1000;
		money = 1000;
		feedTypeCountMap = new HashMap<AbsPetFeed, Integer>();
	}
    
	public void setIntimacyChangeListener(
			OnPetIntimacyChangeListener intimacyChangeListener) {
		this.intimacyChangeListener = intimacyChangeListener;
	}
 
	public void setLevelUpListener(OnPetLevelUpListener levelUpListener) {
		this.levelUpListener = levelUpListener;
	}

	public void setMoneyChangeListner(OnPetMoneyChangeListener moneyChangeListner) {
		this.moneyChangeListner = moneyChangeListner;
	}


	public void setPowerChangeListener(OnPetPowerChangeListener powerChangeListener) {
		this.powerChangeListener = powerChangeListener;
	}


	public int getLevel() {
		return level;
	}

	public int getIntimacy() {
		return intimacy;
	}

	public int getPower() {
		return power;
	}
	public int getMoney() {
		return money;
	}
	
	public void setMoney(int money) {
		this.money = money;
		if (moneyChangeListner != null) {
			moneyChangeListner.onPetMoneyChange(this);
		}
	}
	public void setPower(int power) {
		this.power = power;
		if (powerChangeListener != null) {
			powerChangeListener.onPetPowerChange(this);
		}
	}
	public void setIntimacy(int intimacy) {
		this.intimacy = intimacy;
		if (this.intimacy >= 100) {
			levelUp();
		}
		if (intimacyChangeListener != null) {
			intimacyChangeListener.onPetIntimacyChange(this);
		}
	}
	private void levelUp() {
		this.level += 1;
		intimacy = 0;
		
		// TODO: AbsPetFeed thefeed = getTheMostUsedFeed();
		
		if (levelUpListener != null) {
			levelUpListener.onPetLevelUp(this);
		}
	}
	
	private AbsPetFeed getTheMostUsedFeed() {
		Integer theMaxCount = 0;
		AbsPetFeed theMaxUsedFeed = null;
		for (Entry<AbsPetFeed, Integer> entry : feedTypeCountMap.entrySet()) {
			if (entry.getValue() > theMaxCount) {
				theMaxCount = entry.getValue();
				theMaxUsedFeed = entry.getKey();
			}
		}
		return theMaxUsedFeed;
	}

	public void feed(AbsPetFeed feed) {
		feed.updatePetAttributes(this);
		updateDateFeedCount(feed);
	}

	private void updateDateFeedCount(AbsPetFeed feed) {
		if (!feedTypeCountMap.containsKey(feed)) {
			feedTypeCountMap.put(feed, 1);
		} else {
			feedTypeCountMap.put(feed, feedTypeCountMap.get(feed) + 1);
		}
	}
	
}
