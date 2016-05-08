package ro.unitbv.fmi.tmis.platform.service;

import java.io.File;
import java.net.URISyntaxException;

import javax.ejb.Remote;

@Remote
public interface IHdfsService {
	public void createDirectory(String path);
	
	public void uploadFile(String path, File file) throws URISyntaxException;
}
