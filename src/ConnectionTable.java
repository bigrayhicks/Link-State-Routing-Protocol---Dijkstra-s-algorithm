package beans;

public class ConnectionTable {
	private int routerNo;
	private double weight;
	private int nextHop;
	/**
	 * @return the routerNo
	 */
	public int getRouterNo() {
		return routerNo;
	}
	/**
	 * @param routerNo the routerNo to set
	 */
	public void setRouterNo(int routerNo) {
		this.routerNo = routerNo;
	}
	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * @return the nextHop
	 */
	public int getNextHop() {
		return nextHop;
	}
	/**
	 * @param nextHop the nextHop to set
	 */
	public void setNextHop(int nextHop) {
		this.nextHop = nextHop;
	}
	
}
