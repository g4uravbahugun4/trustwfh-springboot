package com.portals.trustwfh.dto;

import java.util.List;

public class PunchInEventMessage {
    private String userId;
    private double confidence;
    private String ipAddress;
    private String deviceFingerprint;
    private String deviceToken;
    private List<SignalMessage> signals;

    public PunchInEventMessage() {
    }

    public PunchInEventMessage(String userId, double confidence,
            String ipAddress, String deviceFingerprint, String deviceToken,
            List<SignalMessage> signals) {
        this.userId = userId;
        this.confidence = confidence;
        this.ipAddress = ipAddress;
        this.deviceFingerprint = deviceFingerprint;
        this.deviceToken = deviceToken;
        this.signals = signals;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceFingerprint() {
        return deviceFingerprint;
    }

    public void setDeviceFingerprint(String deviceFingerprint) {
        this.deviceFingerprint = deviceFingerprint;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public List<SignalMessage> getSignals() {
        return signals;
    }

    public void setSignals(List<SignalMessage> signals) {
        this.signals = signals;
    }

    public static class SignalMessage {
        private String signalName;
        private double score;
        private double weight;
        private String remark;

        public SignalMessage() {
        }

        public SignalMessage(String signalName, double score, double weight, String remark) {
            this.signalName = signalName;
            this.score = score;
            this.weight = weight;
            this.remark = remark;
        }

        public String getSignalName() {
            return signalName;
        }

        public void setSignalName(String signalName) {
            this.signalName = signalName;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
