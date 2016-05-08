package ro.unitbv.fmi.tmis.platform.service;

import java.io.File;
import java.net.URISyntaxException;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class HdfsServicehadoopAPI implements IHdfsService{

	@Override
	public void createDirectory(String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uploadFile(String path, File file) throws URISyntaxException {
		// TODO Auto-generated method stub
		
	}

}
