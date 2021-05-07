package com.weisbrja;

import com.weisbrja.event.EventBus;
import com.weisbrja.jetty.JettyServer;
import com.weisbrja.simulation.Boundary2d;
import com.weisbrja.simulation.Boundary4d;
import com.weisbrja.view.ViewJSONDataPreparer;

public class App {

	public static void main(String[] args) {
		/*
			TODO:
			Buttons und Responses programmieren
		*/

		// TODO: 2/3/21 Implement changing random seed at beginning
		EventBus eventBus = new EventBus();

		RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator(987654351976349L);

		boolean stopCreaturesWhenOnGround = true;

		double gravityY = 2d;
		double groundDamping = 0.8d;
		double airFriction = 0.8d;

		Boundary4d circlePositionBoundaries = new Boundary4d(-80d, 80d, -160d, 0d);
		Boundary2d circleRadiusBoundaries = new Boundary2d(10d, 20d);

		double maxMuscleForce = 10d;
		Boundary2d muscleStrengthBoundaries = new Boundary2d(10d, 30d);
		Boundary2d muscleLengthPhasesBoundaries = new Boundary2d(50d, 120d);
		Boundary2d muscleClockSpeedBoundaries = new Boundary2d(0d, 0.05d);

		Boundary2d creatureMutationRateBoundaries = new Boundary2d(0.001d, 0.2d);
		Boundary2d creatureStructuralMutationRateBoundaries = new Boundary2d(0.001d, 0.1d);


		AppContext.newInstance(
			eventBus,

			randomNumberGenerator,

			stopCreaturesWhenOnGround,

			gravityY,
			groundDamping,
			airFriction,

			circlePositionBoundaries,
			circleRadiusBoundaries,

			maxMuscleForce,
			muscleStrengthBoundaries,
			muscleLengthPhasesBoundaries,
			muscleClockSpeedBoundaries,

			creatureMutationRateBoundaries,
			creatureStructuralMutationRateBoundaries
		);

		new ViewJSONDataPreparer();

		JettyServer jettyServer = new JettyServer(8080);
		jettyServer.start();
	}
}
