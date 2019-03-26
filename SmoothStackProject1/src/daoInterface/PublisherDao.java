package daoInterface;

import java.util.ArrayList;
import pojos.Publisher;

public interface PublisherDao {
	public void addPublisher(Publisher publisher);
	public Publisher getPublisher(int publisherId);
	public ArrayList<Publisher> getAllPublishers();
	public void updatePublisher(Publisher publisher);
	public void deletePublisher(Publisher publisher);
}