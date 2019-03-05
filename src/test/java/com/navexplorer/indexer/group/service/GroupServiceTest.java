package com.navexplorer.indexer.group.service;

import com.navexplorer.indexer.group.Group;
import com.navexplorer.indexer.group.GroupCategory;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.navexplorer.indexer.group.service.GroupService;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class GroupServiceTest {
    @Test
    public void it_will_provide_and_empty_List_when_none_are_matched() {
        GroupService groupService = new GroupService();
        List<Group> groups = groupService.getGroupForCategory(GroupCategory.HOURLY, 0);

        assertThat(groups.size()).isEqualTo(0);
    }

    @Test
    public void it_can_group_by_hour() {
        GroupService groupService = new GroupService();
        List<Group> groups = groupService.getGroupForCategory(GroupCategory.HOURLY, 1);

        assertThat(groups.get(0).getSecondsInPeriod()).isLessThanOrEqualTo(3599);
    }

    @Test
    public void it_can_return_the_hourly_group() {
        GroupService groupService = new GroupService();
        List<Group> groups = groupService.getGroupForCategory(GroupCategory.HOURLY, 10);

        Calendar expectedStart = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Calendar expectedEnd = (Calendar) expectedStart.clone();
        expectedEnd.set(Calendar.MINUTE, 0);
        expectedEnd.set(Calendar.SECOND, 0);

        groups.stream().findFirst().ifPresent(g -> {
            assertThat(g.getCategory()).isEqualTo(GroupCategory.HOURLY);
            assertThat(g.getSecondsInPeriod()).isLessThanOrEqualTo(3599);
            assertThat(g.getStart().get(Calendar.HOUR_OF_DAY)).isEqualTo(expectedStart.get(Calendar.HOUR_OF_DAY));
            assertThat(g.getEnd().get(Calendar.HOUR_OF_DAY)).isEqualTo(expectedEnd.get(Calendar.HOUR_OF_DAY));
        });

        expectedStart.set(Calendar.MINUTE, 59);
        expectedStart.set(Calendar.SECOND, 59);

        groups.stream().skip(1).forEach(g -> {
            expectedStart.add(Calendar.HOUR_OF_DAY, -1);
            expectedEnd.add(Calendar.HOUR_OF_DAY, -1);

            assertThat(g.getCategory()).isEqualTo(GroupCategory.HOURLY);
            assertThat(g.getSecondsInPeriod()).isEqualTo(3599);
            assertThat(g.getStart().get(Calendar.HOUR_OF_DAY)).isEqualTo(expectedStart.get(Calendar.HOUR_OF_DAY));
            assertThat(g.getEnd().get(Calendar.HOUR_OF_DAY)).isEqualTo(expectedEnd.get(Calendar.HOUR_OF_DAY));
        });
    }
}
