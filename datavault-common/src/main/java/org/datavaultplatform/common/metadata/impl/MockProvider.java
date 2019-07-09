package org.datavaultplatform.common.metadata.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.datavaultplatform.common.model.Dataset;
import org.datavaultplatform.common.metadata.Provider;

// This mock metadata provider is for testing purposes only

public class MockProvider implements Provider {

    List<Dataset> datasets = new ArrayList<>();
    
    public MockProvider() {
        for (int i = 1; i < 6; i++) {
            Dataset d = new Dataset();
            d.setID("MOCK-DATASET-" + i);
            d.setName("Sample dataset " + i);
            d.setContent("Mock Metadata");
            d.setVisible(true);
            datasets.add(d);
        }
    }
    
    @Override
    public List<Dataset> getDatasetsForUser(String userID) {
        return datasets;
    }
    
    @Override
    public Dataset getDataset(String id) {
        for (Dataset d : datasets) {
            if (d.getID().equals(id)) {
                return d;
            }
        }
        return null;
    }

	@Override
	public Map<String, String> getPureProjectIds() {
		Map<String, String> projectIds = new HashMap<>();
		for (Dataset d : datasets) {
			String replace = d.getID().replace("MOCK-DATASET-", "MOCK-PROJECTID-");
			projectIds.put(d.getID(), replace);
		}
		return projectIds;
	}
}