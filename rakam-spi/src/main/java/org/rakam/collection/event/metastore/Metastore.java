package org.rakam.collection.event.metastore;

import org.rakam.analysis.ProjectNotExistsException;
import org.rakam.collection.SchemaField;
import org.rakam.util.ProjectCollection;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;


public interface Metastore {
    Map<String, Collection<String>> getAllCollections();

    Map<String, List<SchemaField>> getCollections(String project);

    Set<String> getCollectionNames(String project);

    void createProject(String project);

    Set<String> getProjects();

    List<SchemaField> getCollection(String project, String collection);

    List<SchemaField> createOrGetCollectionField(String project, String collection, List<SchemaField> fields, Consumer<ProjectCollection> newCollectionListener) throws ProjectNotExistsException;
}