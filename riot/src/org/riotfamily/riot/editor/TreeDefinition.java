/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 * 
 * The Original Code is Riot.
 * 
 * The Initial Developer of the Original Code is
 * Neteye GmbH.
 * Portions created by the Initial Developer are Copyright (C) 2006
 * the Initial Developer. All Rights Reserved.
 * 
 * Contributor(s):
 *   Felix Gnass [fgnass at neteye dot de]
 *   Alf Werder [alf dot werder at artundweise dot de]
 * 
 * ***** END LICENSE BLOCK ***** */
package org.riotfamily.riot.editor;

import org.riotfamily.common.i18n.MessageResolver;
import org.riotfamily.riot.editor.ui.EditorReference;
import org.springframework.util.Assert;

public class TreeDefinition extends ListDefinition implements TreeList {
	
	protected static final String TYPE_TREE = "tree";
	
	private FormDefinition formDefinition;
	
	private ListDefinition nodeListDefinition;
	
	
	public TreeDefinition(EditorRepository editorRepository) {
		super(editorRepository);
	}
	
	private boolean isNode(Object bean) {
		if (bean == null) {
			return false;
		}
		if (!nodeListDefinition.getBeanClass().isInstance(bean)) {
			return false;
		}
		
		EditorDefinition parentDef = getParentEditorDefinition();
		
		if (parentDef != null && !(parentDef instanceof GroupDefinition)) {
			Class parentClass = parentDef.getBeanClass();
			if (parentClass.isInstance(bean)) {
				return false;
			}
		}
		return true;
	}

	public ListDefinition getNodeListDefinition() {
		return this.nodeListDefinition;
	}

	public void setDisplayDefinition(final EditorDefinition def) {
		Assert.isInstanceOf(FormDefinition.class, def);
		super.setDisplayDefinition(def);
		
		formDefinition = (FormDefinition) def;
		nodeListDefinition = new NodeListDefinition();
		
		getEditorRepository().addEditorDefinition(nodeListDefinition);
		formDefinition.getChildEditorDefinitions().add(0, nodeListDefinition);
		
		FormDefinition nodeForm = formDefinition.copy("node-");
		nodeForm.setParentEditorDefinition(nodeListDefinition);
		nodeListDefinition.setDisplayDefinition(nodeForm);
		nodeListDefinition.setParentEditorDefinition(nodeForm);
	}
	
	public EditorReference createEditorPath(Object bean,
		MessageResolver messageResolver) {
		
		if (isNode(bean)) {
			return nodeListDefinition.createEditorPath(bean, messageResolver);
		}
		
		return super.createEditorPath(bean, messageResolver);
	}

	public class NodeListDefinition extends ListDefinition implements TreeList {

		NodeListDefinition() {
			super(TreeDefinition.this, TreeDefinition.this.getEditorRepository());
			setCondition(TreeDefinition.this.getCondition());
		}
		
		public String getId() {
			return "node-" + super.getId();
		}

		public TreeDefinition getTreeDefinition() {
			return TreeDefinition.this;
		}
		
		public ListDefinition getNodeListDefinition() {
			return this;
		}
		
		private EditorReference stripList(EditorReference path) {
			EditorReference parent = path.getParent();
			parent.setEditorUrl(path.getEditorUrl());
			parent.setEditorType("node");
			return parent;
		}
		
		public EditorReference createEditorPath(String objectId, 
				String parentId, MessageResolver messageResolver) {
			
			return stripList(super.createEditorPath(
					objectId, parentId, messageResolver));
		}
		
		public EditorReference createEditorPath(Object bean, 
				MessageResolver messageResolver) {
					
			if (isNode(bean)) {
				return stripList(super.createEditorPath(bean, messageResolver));
			}
			else {
				return TreeDefinition.this.createEditorPath(
						bean, messageResolver);
			}
		}
		
	}

}
