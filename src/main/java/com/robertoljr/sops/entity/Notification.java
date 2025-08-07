package com.robertoljr.sops.entity;

import com.robertoljr.sops.constant.notification.Channel;
import com.robertoljr.sops.constant.notification.Status;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false)
    private Channel channel;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "message", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "sent_at", nullable = false)
    private Instant sentAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.sentAt = null;
    }

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public void setSentAt(Instant sentAt) {
        this.sentAt = sentAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUser(), that.getUser()) && Objects.equals(getTransaction(), that.getTransaction()) && getChannel() == that.getChannel() && Objects.equals(getDestination(), that.getDestination()) && Objects.equals(getSubject(), that.getSubject()) && Objects.equals(getMessage(), that.getMessage()) && getStatus() == that.getStatus() && Objects.equals(getCreatedAt(), that.getCreatedAt()) && Objects.equals(getSentAt(), that.getSentAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getTransaction(), getChannel(), getDestination(), getSubject(), getMessage(), getStatus(), getCreatedAt(), getSentAt());
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", transaction=" + transaction.getId() +
                ", channel=" + channel +
                ", destination='" + destination + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", sentAt=" + sentAt +
                '}';
    }
}
