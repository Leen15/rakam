package org.rakam.analysis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

public interface ApiKeyService {
    ProjectApiKeys createApiKeys(String project);

    String getProjectOfApiKey(String apiKey, AccessKeyType type);

    void revokeApiKeys(String project, String masterKey);

    boolean checkPermission(String project, AccessKeyType type, String apiKey);

    void revokeAllKeys(String project);

    @AutoValue
    abstract class ProjectApiKeys {
        @JsonProperty("master_key") public abstract String masterKey();
        @JsonProperty("read_key") public abstract String readKey();
        @JsonProperty("write_key") public abstract String writeKey();

        @JsonCreator
        public static ProjectApiKeys create(@JsonProperty("master_key") String masterKey,
                                     @JsonProperty("read_key") String readKey,
                                     @JsonProperty("write_key") String writeKey) {
            return new AutoValue_ApiKeyService_ProjectApiKeys(masterKey, readKey, writeKey);
        }

        public String getKey(AccessKeyType accessKeyType) {
            switch (accessKeyType) {
                case WRITE_KEY:
                    return writeKey();
                case MASTER_KEY:
                    return masterKey();
                case READ_KEY:
                    return readKey();
                default:
                    throw new IllegalStateException();
            }
        }
    }

    enum AccessKeyType {
        MASTER_KEY("master_key"), READ_KEY("read_key"), WRITE_KEY("write_key");

        private final String key;

        AccessKeyType(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        public static AccessKeyType fromKey(String key) {
            for (AccessKeyType accessKeyType : values()) {
                if (accessKeyType.getKey().equals(key)) {
                    return accessKeyType;
                }
            }
            throw new IllegalArgumentException(key + " doesn't exist.");
        }
    }
}
