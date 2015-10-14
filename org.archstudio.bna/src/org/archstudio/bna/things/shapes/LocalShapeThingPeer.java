package org.archstudio.bna.things.shapes;

import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import org.archstudio.bna.IBNAView;
import org.archstudio.bna.ICoordinate;
import org.archstudio.bna.ICoordinateMapper;
import org.archstudio.bna.facets.peers.IHasShadowPeer;
import org.archstudio.bna.things.AbstractThingPeer;
import org.archstudio.bna.ui.IUIResources;
import org.archstudio.bna.utils.BNAUtils;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;

@NonNullByDefault
public class LocalShapeThingPeer<T extends LocalShapeThing> extends AbstractThingPeer<T> implements IHasShadowPeer<T> {

	public LocalShapeThingPeer(T thing, IBNAView view, ICoordinateMapper cm) {
		super(thing, view, cm);
	}

	protected Shape createLocalShape() {
		Path2D shape = new Path2D.Double(t.getShape());
		Dimension size = t.getSize();
		shape.transform(AffineTransform.getScaleInstance(size.getWidth(), size.getHeight()));
		Point2D localPoint = cm.worldToLocal(t.getAnchorPoint());
		shape.transform(AffineTransform.getTranslateInstance(localPoint.getX(), localPoint.getY()));
		return shape;
	}

	@Override
	public boolean draw(Rectangle localBounds, IUIResources r) {
		Shape localShape = createLocalShape();

		RGB glowColor = t.getGlowColor();
		if (glowColor != null) {
			r.glowShape(localShape, glowColor, t.getGlowWidth(), t.getGlowAlpha());
		}
		RGB color = t.getColor();
		if (color != null) {
			r.fillShape(localShape, color, t.isGradientFilled() ? t.getSecondaryColor() : null, t.getAlpha());
		}
		if (t.isSelected()) {
			r.selectShape(localShape, t.getRotatingOffset());
		}
		else {
			r.drawShape(localShape, t.getEdgeColor(), t.getLineWidth(), t.getLineStyle(), 1);
		}

		return true;
	}

	@Override
	public boolean drawShadow(Rectangle localBounds, IUIResources r) {
		if (t.getGlowColor() == null && t.getColor() != null) {
			Shape localShape = createLocalShape();
			if (!localBounds.intersects(BNAUtils.toRectangle(localShape.getBounds2D()))) {
				return false;
			}

			r.fillShape(localShape, new RGB(0, 0, 0), null, 1);
			return true;
		}
		return false;
	}

	@Override
	public boolean isInThing(ICoordinate location) {
		Point localPoint = location.getLocalPoint();
		return createLocalShape().contains(localPoint.x, localPoint.y);
	}

}
