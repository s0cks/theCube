package theCube.ui;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public final class Cross
implements Shape {
    private final Rectangle bounds;

    public Cross(Rectangle bounds){
        this.bounds = bounds;
    }

    public Cross(int x, int y, int width, int height){
        this(new Rectangle(x, y, width, height));
    }

    @Override
    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return this.bounds;
    }

    @Override
    public boolean contains(double v, double v1) {
        return this.bounds.contains(v, v1);
    }

    @Override
    public boolean contains(Point2D point2D) {
        return this.bounds.contains(point2D);
    }

    @Override
    public boolean intersects(double v, double v1, double v2, double v3) {
        return this.bounds.intersects(v, v1, v2, v3);
    }

    @Override
    public boolean intersects(Rectangle2D rectangle2D) {
        return this.bounds.intersects(rectangle2D);
    }

    @Override
    public boolean contains(double v, double v1, double v2, double v3) {
        return this.bounds.contains(v, v1, v2, v3);
    }

    @Override
    public boolean contains(Rectangle2D rectangle2D) {
        return this.bounds.contains(rectangle2D);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform affineTransform) {
        return new CrossIterator(this.getBounds(), affineTransform);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform affineTransform, double v) {
        return this.getPathIterator(affineTransform);
    }

    private static final class CrossIterator
    implements PathIterator{
        private final Rectangle rect;
        private final AffineTransform affine;
        private int index;

        private CrossIterator(Rectangle rect, AffineTransform affine){
            this.rect = rect;
            this.affine = affine;
        }

        @Override
        public int getWindingRule() {
            return 1;
        }

        @Override
        public boolean isDone() {
            return this.index > 3;
        }

        @Override
        public void next() {
            this.index++;
        }

        @Override
        public int currentSegment(float[] floats) {
            if(this.isDone()){
                throw new NullPointerException("cross iterator out of bounds");
            } else{

                if(this.affine != null){
                    switch(this.index){
                        case 0:{
                            floats[0] = this.rect.x;
                            floats[1] = this.rect.y;
                            break;
                        }
                        case 1:{
                            floats[0] = this.rect.x + this.rect.width;
                            floats[1] = this.rect.y + this.rect.height;
                            break;
                        }
                        case 2:{
                            floats[0] = this.rect.x;
                            floats[1] = this.rect.y + this.rect.height;
                            break;
                        }
                        case 3:{
                            floats[0] = this.rect.x + this.rect.width;
                            floats[1] = this.rect.y;
                        }
                        default:{
                            break;
                        }
                    }

                    this.affine.transform(floats, 0, floats, 0, 2);
                }

                return 0;
            }
        }

        @Override
        public int currentSegment(double[] doubles) {
            if(this.isDone()){
                throw new NullPointerException("cross iterator out of bounds");
            } else{

                if(this.affine != null){
                    switch(this.index){
                        case 0:{
                            doubles[0] = this.rect.x;
                            doubles[1] = this.rect.y;
                            break;
                        }
                        case 1:{
                            doubles[0] = this.rect.x + this.rect.width;
                            doubles[1] = this.rect.y + this.rect.height;
                            break;
                        }
                        case 2:{
                            doubles[0] = this.rect.x;
                            doubles[1] = this.rect.y + this.rect.height;
                            break;
                        }
                        case 3:{
                            doubles[0] = this.rect.x + this.rect.width;
                            doubles[1] = this.rect.y;
                        }
                        default:{
                            break;
                        }
                    }

                    this.affine.transform(doubles, 0, doubles, 0, 2);
                }

                return 0;
            }
        }
    }
}