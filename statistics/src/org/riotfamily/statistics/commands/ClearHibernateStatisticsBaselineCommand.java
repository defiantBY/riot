/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.riotfamily.statistics.commands;

import org.hibernate.SessionFactory;
import org.riotfamily.core.screen.list.command.CommandContext;
import org.riotfamily.core.screen.list.command.CommandResult;
import org.riotfamily.core.screen.list.command.Selection;
import org.riotfamily.core.screen.list.command.impl.support.AbstractCommand;
import org.riotfamily.core.screen.list.command.result.BatchResult;
import org.riotfamily.core.screen.list.command.result.NotificationResult;
import org.riotfamily.core.screen.list.command.result.RefreshListResult;

public class ClearHibernateStatisticsBaselineCommand extends AbstractCommand {

	private SessionFactory sessionFactory;
	
	@Override
	protected String getName() {
		return "clearBaseline";
	}

	@Override
	protected String getAction() {
		return "admin";
	}
	
	@Override
	protected String getIcon() {
		return "chart_bar_delete";
	}
	
	public ClearHibernateStatisticsBaselineCommand(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public CommandResult execute(CommandContext context, Selection selection) {
		sessionFactory.getStatistics().clear();
		return new BatchResult(
				new RefreshListResult(),
				new NotificationResult(context, this)
						.setDefaultMessage("The baseline has been cleared."));
	}
}
