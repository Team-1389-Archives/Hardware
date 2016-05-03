package com.team1389.hardware.util.state;

public class StateTracker {
	State lastState;
	
	public StateTracker(){
		lastState = null;
	}
	
	public State newState(StateSetup setup){
		State state = new TrackedState(setup);
		return state;
	}
	
	private class TrackedState implements State{
		
		StateSetup setup;
		
		public TrackedState(StateSetup setup) {
			this.setup = setup;
		}

		@Override
		public void init() {
			if (lastState != this){
				lastState = this;
				setup.setup();
			}
		}
		
	}
}
