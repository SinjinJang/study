package sqlite_test.vo;

public class Folder {
	private String name;
	private String parent;
	private Folder[] children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Folder[] getChildren() {
		return children;
	}

	public void setChildren(Folder[] children) {
		this.children = children;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
}
