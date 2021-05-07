package com.weisbrja.view;


import com.weisbrja.AppContext;
import com.weisbrja.simulation.Circle;
import com.weisbrja.simulation.CreatureDrawEvent;
import com.weisbrja.simulation.Muscle;

import javax.vecmath.Vector2d;

public class ViewJSONDataPreparer {

	private final Vector2d cameraPosition;
	private final Vector2d cameraPositionOffset;

	public ViewJSONDataPreparer() {
		cameraPositionOffset = new Vector2d(300d, 500d);
		cameraPosition = (Vector2d) cameraPositionOffset.clone();
		cameraPosition.scale(-1d);

		AppContext.getInstance().getEventBus().listenFor(CreatureDrawEvent.class, this::handleCreatureDraw);
	}

	private void handleCreatureDraw(CreatureDrawEvent creatureDrawEvent) {
		StringBuilder viewJSONData = new StringBuilder("{\"m\":[");

		// prepare the muscle's view data
		int musclesSize = creatureDrawEvent.getCreature().getMuscles().size();
		for (int i = 0; i < musclesSize; ++i) {
			Muscle muscle = creatureDrawEvent.getCreature().getMuscles().get(i);
			double alphaPercentageStart = 50d / 255d;
			double alphaPercentageEnd = 1d;
			double alphaPercentage = alphaPercentageStart + (alphaPercentageEnd - alphaPercentageStart) / (AppContext.getInstance().getMuscleStrengthBoundaries().getMax() - AppContext.getInstance().getMuscleStrengthBoundaries().getMin()) * (muscle.getStrength() - AppContext.getInstance().getMuscleStrengthBoundaries().getMin());
			String fill = (muscle.getExpanding() ? "200,50,50," : "50,50,200,") + alphaPercentage;
			viewJSONData.append("{\"x0\":%s,\"y0\":%s,\"x1\":%s,\"y1\":%s,\"e\":%s,\"f\":\"rgba(%s)\"}".formatted(muscle.getCircle1().getPosition().getX(), muscle.getCircle1().getPosition().getY(), muscle.getCircle2().getPosition().getX(), muscle.getCircle2().getPosition().getY(), muscle.getExpanding(), fill));
			if (i + 1 < musclesSize)
				viewJSONData.append(',');
		}
		viewJSONData.append("],\"c\":[");

		// prepare the circle's view data
		int circlesSize = creatureDrawEvent.getCreature().getCircles().size();
		for (int i = 0; i < circlesSize; ++i) {
			Circle circle = creatureDrawEvent.getCreature().getCircles().get(i);
			int grayScaleStart = 255;
			int grayScaleEnd = 0;
			int grayScale = (int) (grayScaleStart + (grayScaleEnd - grayScaleStart) * circle.getFrictionPercentage());
			String fill = "%d,%d,%d".formatted(grayScale, grayScale, grayScale);
			viewJSONData.append("{\"x\":%s,\"y\":%s,\"r\":%s,\"f\":\"rgb(%s)\"}".formatted(circle.getPosition().getX(), circle.getPosition().getY(), circle.getRadius(), fill));
			if (i + 1 < circlesSize)
				viewJSONData.append(',');
		}

		// update the camera position
		Vector2d cameraTargetPosition = (Vector2d) creatureDrawEvent.getCreature().getPosition().clone();
		cameraTargetPosition.sub(cameraPositionOffset);
		cameraPosition.interpolate(cameraTargetPosition, 0.02d);
		viewJSONData.append("],\"v\":{\"x\":%s,\"y\":%s}}".formatted(cameraPosition.getX(), cameraPosition.getY()));

		AppContext.getInstance().getEventBus().emit(new ViewJSONDataSendEvent(viewJSONData.toString()));
	}
}
