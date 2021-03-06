package org.apereo.cas.pm.web.flow.actions;

import org.apereo.cas.category.MailCategory;
import org.apereo.cas.pm.web.flow.PasswordManagementWebflowUtils;
import org.apereo.cas.ticket.TransientSessionTicket;
import org.apereo.cas.ticket.TransientSessionTicketFactory;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.HttpRequestUtils;
import org.apereo.cas.util.junit.ConditionalIgnore;
import org.apereo.cas.util.junit.RunningContinuousIntegrationCondition;

import lombok.val;
import org.apereo.inspektr.common.web.ClientInfo;
import org.apereo.inspektr.common.web.ClientInfoHolder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.test.MockRequestContext;

import java.io.Serializable;

import static org.junit.Assert.*;

/**
 * This is {@link VerifyPasswordResetRequestActionTests}.
 *
 * @author Misagh Moayyed
 * @since 5.3.0
 */
@ConditionalIgnore(condition = RunningContinuousIntegrationCondition.class, port = 25000)
@Category(MailCategory.class)
public class VerifyPasswordResetRequestActionTests extends BasePasswordManagementActionTests {
    @Test
    public void verifyAction() {
        try {
            val context = new MockRequestContext();
            val request = new MockHttpServletRequest();
            context.setExternalContext(new ServletExternalContext(new MockServletContext(), request, new MockHttpServletResponse()));
            assertEquals("error", verifyPasswordResetRequestAction.execute(context).getId());

            request.setRemoteAddr("1.2.3.4");
            request.setLocalAddr("1.2.3.4");
            request.addHeader(HttpRequestUtils.USER_AGENT_HEADER, "test");
            ClientInfoHolder.setClientInfo(new ClientInfo(request));
            val token = passwordManagementService.createToken("casuser");
            val transientFactory = (TransientSessionTicketFactory) this.ticketFactory.get(TransientSessionTicket.class);
            val serverPrefix = casProperties.getServer().getPrefix();
            val service = webApplicationServiceFactory.createService(serverPrefix);
            val properties = CollectionUtils.<String, Serializable>wrap(PasswordManagementWebflowUtils.FLOWSCOPE_PARAMETER_NAME_TOKEN, token);
            val ticket = transientFactory.create(service, properties);
            this.ticketRegistry.addTicket(ticket);
            request.addParameter(PasswordManagementWebflowUtils.REQUEST_PARAMETER_NAME_PASSWORD_RESET_TOKEN, ticket.getId());
            context.setExternalContext(new ServletExternalContext(new MockServletContext(), request, new MockHttpServletResponse()));
            assertEquals("success", verifyPasswordResetRequestAction.execute(context).getId());

            assertTrue(PasswordManagementWebflowUtils.isPasswordResetSecurityQuestionsEnabled(context));
            assertNotNull(PasswordManagementWebflowUtils.getPasswordResetUsername(context));
            assertNotNull(PasswordManagementWebflowUtils.getPasswordResetToken(context));
        } catch (final Exception e) {
            throw new AssertionError(e.getMessage(), e);
        }
    }
}
