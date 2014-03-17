package lab1;

import java.util.Arrays;

public class ClosestPair {

	public final static double INF = java.lang.Double.POSITIVE_INFINITY;

	/**
	 * Given a collection of points, find the closest pair of point and the
	 * distance between them in the form "(x1, y1) (x2, y2) distance"
	 * 
	 * @param pointsByX
	 *            points sorted in nondecreasing order by X coordinate
	 * @param pointsByY
	 *            points sorted in nondecreasing order by Y coordinate
	 * @return Result object containing the points and distance
	 */
	static Result findClosestPair(XYPoint pointsByX[], XYPoint pointsByY[]) {
		int numEle = pointsByX.length;
		int largeHalf = (int) Math.ceil((double) numEle / 2);
		if (numEle <= 8) { //we use a base case of 8; it is significantly faster than base case 3
			return findClosestPairBruteForce(pointsByX, pointsByY); //brute-force
		}
		XYPoint[] pointsByXL = new XYPoint[numEle / 2]; //now we create the left and right arrays
		XYPoint[] pointsByXR = new XYPoint[largeHalf]; //we designate the right side as the larger one in case we are dividing an odd number
		XYPoint[] pointsByYL = new XYPoint[numEle / 2];
		XYPoint[] pointsByYR = new XYPoint[largeHalf];
		/*
		 * We copy pointsByX to the right and left sides
		 */
		pointsByXL = Arrays.copyOfRange(pointsByX, 0, numEle/2); 
		pointsByXR = Arrays.copyOfRange(pointsByX, numEle/2, numEle);
		/*
		 * We iterate through pointsByY; if the x is left of the 1st element in XR, it belongs on the left side, and vice versa
		 * The function isLeftOf allows us to deal with duplicates (it's special that way)
		 */
		int a = 0; //index for YL
		int b = 0; //index for YR
		for (int j = 0; j < numEle; j++) {
			if (pointsByY[j].isLeftOf(pointsByXR[0])) {
				pointsByYL[a] = pointsByY[j];
				a++;
			} else {
				pointsByYR[b] = pointsByY[j];
				b++;
			}
		}
		Result lMin = findClosestPair(pointsByXL, pointsByYL); //recursive calls (left/right)
		Result rMin = findClosestPair(pointsByXR, pointsByYR);
		Result lRMin = lMin;
		if (Math.min(lMin.dist, rMin.dist) == rMin.dist) { // prioritizes left min from right
			lRMin = rMin;
		}
		/*
		 * Iterates through pointsByY, adding points to yStrip that are within lRMin of the midpoint
		 */
		int length = 0; //number of values in the yStrip
		XYPoint[] yStrip = new XYPoint[numEle]; 
		for (int p = 0; p < numEle; p++) {
			if (abs(pointsByY[p].x - (pointsByX[numEle / 2].x + pointsByX[numEle / 2 - 1].x) / 2) <= lRMin.dist) { //if the x dist from the midpoint is less than lRMin.dist
				yStrip[length] = pointsByY[p];
				length++;
			}
		}
		/*
		 * Iterates through yStrip, comparing distance of y values with each next element; stops at second to last element
		 * Requires must be more than 1 element
		 */
		if (length > 1) {
			for (int i = 0; i < length-1; i++) {
				int k = i + 1;
				while (abs(yStrip[i].y - yStrip[k].y) <= lRMin.dist) {
					lRMin = new Result(yStrip[i], yStrip[k],
							yStrip[k].dist(yStrip[i]));
					if (k + 1 != length) {
						k++;
					}
					else { //ensures it breaks out of the loop before it points to a null value 
						 break;
					}
				}
			}
		}
		return lRMin;
	}
	/**
	 * Our brute force method, if just one element, returns the only element and INF distance
	 * Otherwise, compares the distance of each element 
	 * @param pointsByX
	 * @return
	 */
	static Result solve(XYPoint[] pointsByX) {
		//FIXME: ACTUALLY THIS IS MEANT FOR 3 ELEMENTS, SHOULD CHANGE TO REAL BRUTE FORCE
		Result r = new Result(pointsByX[0], pointsByX[0], INF);
		if (pointsByX.length > 1) {
			for (int i = 0; i < pointsByX.length - 1; i++) {
				if (pointsByX[i].dist(pointsByX[i + 1]) < r.dist) {
					r = new Result(pointsByX[i], pointsByX[i + 1],
							pointsByX[i].dist(pointsByX[i + 1]));
				}
			}
		}
		return r;
	}
	static int abs(int x) {
		if (x < 0) {
			return -x;
		} else {
			return x;
		}
	}
	static Result findClosestPairBruteForce(XYPoint pointsByX[], XYPoint pointsByY[]) {
		Result r = new Result(pointsByX[0], pointsByX[0], INF);
		if(pointsByX.length>1) {
			for (int i = 0; i < pointsByX.length; i++) {
				for (int j = i + 1; j < pointsByX.length; j++) {
					if (pointsByX[i].dist(pointsByX[j]) < r.dist) {
						r = new Result(pointsByX[i], pointsByX[j], pointsByX[i].dist(pointsByX[j]));
					}
				}
			}
		}
		return r;
	}
}