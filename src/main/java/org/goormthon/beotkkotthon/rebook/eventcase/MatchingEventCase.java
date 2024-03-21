package org.goormthon.beotkkotthon.rebook.eventcase;

import org.goormthon.beotkkotthon.rebook.event.EnterChallengeWaitRoomEvent;

public interface MatchingEventCase {
    void execute(EnterChallengeWaitRoomEvent event);
}
