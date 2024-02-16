package com.ibsplc.neoicargo.cca.component.feature.getccaassignees;

import com.ibsplc.neoicargo.admin.user.AdminUserWebAPI;
import com.ibsplc.neoicargo.admin.user.model.AdminUserDetailsModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaSelectFilter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
public class GetCcaAssigneesFeatureTest {

    @Mock
    private AdminUserWebAPI adminUserWebAPI;

    @InjectMocks
    private GetCcaAssigneesFeature getCcaAssigneesFeature;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetCcaAssignees()  {
        // Given
        var userModel = new AdminUserDetailsModel();
        userModel.setUserFirstName("Mr.");
        userModel.setUserLastName("Freeze");
        userModel.setUsrIdr("usrIdr");

        var request = getCcaSelectFilter(false, "Mr. Freeze", null);

        // When
        doReturn(List.of(userModel)).when(adminUserWebAPI).fetchUserBasicDetails("ACT", "WEB");

        // Then
        var assigneesPage = getCcaAssigneesFeature.perform(request);
        assertEquals(1, assigneesPage.getEdges().size());
        var assignee = assigneesPage.getEdges().get(0).getNode();
        assertEquals("Mr. Freeze", assignee.getUsername());
        assertEquals("usrIdr", assignee.getUserCode());
    }
}
