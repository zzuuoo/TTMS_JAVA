package xupt.se.ttms.model;

public class Seat {

	private int id;
	private int studioId;
	private int row;
	private int column;
	
	public Seat(){	}

	public Seat(int id, int studioId, int row, int column) {
		super();
		this.id = id;
		this.studioId = studioId;
		this.row = row;
		this.column = column;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudioId() {
		return studioId;
	}

	public void setStudioId(int studioId) {
		this.studioId = studioId;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
}
