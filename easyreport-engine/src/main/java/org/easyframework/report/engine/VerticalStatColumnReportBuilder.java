package org.easyframework.report.engine;

import java.util.List;
import java.util.Map;

import org.easyframework.report.engine.data.ColumnTree;
import org.easyframework.report.engine.data.ColumnTreeNode;
import org.easyframework.report.engine.data.ReportDataRow;
import org.easyframework.report.engine.data.ReportDataSet;
import org.easyframework.report.engine.data.ReportParameter;

/**
 * 纵向展示统计列的报表生成类
 */
public class VerticalStatColumnReportBuilder extends AbstractReportBuilder implements ReportBuilder {

	/**
	 * 纵向展示统计列的报表生成类
	 * 
	 * @param reportDataSet
	 *            报表数据集
	 * @param reportParameter
	 *            报表参数
	 */
	public VerticalStatColumnReportBuilder(ReportDataSet reportDataSet, ReportParameter reportParameter) {
		super(reportDataSet, reportParameter);
	}

	@Override
	public void drawTableBodyRows() {
		ColumnTree leftFixedColumnTree = this.reportDataSet.getBodyLeftFixedColumnTree();
		List<ColumnTreeNode> rowNodes = leftFixedColumnTree.getLastLevelNodes();
		List<ColumnTreeNode> columnNodes = this.reportDataSet.getBodyRightColumnNodes();
		Map<String, ReportDataRow> statRowMap = reportDataSet.getRowMap();
		Map<String, ColumnTreeNode> pathTreeNodeMap = this.getPathTreeNodeMap(leftFixedColumnTree);

		int rowIndex = 0;
		String[] lastNodePaths = null;
		this.tableRows.append("<tbody>");
		for (ColumnTreeNode rowNode : rowNodes) {
			this.tableRows.append("<tr").append(rowIndex % 2 == 0 ? " class=\"easyreport-row\"" : "").append(">");
			lastNodePaths = this.drawLeftFixedColumn(pathTreeNodeMap, lastNodePaths, rowNode, this.reportParameter.isRowSpan());
			for (ColumnTreeNode columnNode : columnNodes) {
				String rowKey = this.reportDataSet.getRowKey(rowNode, columnNode);
				ReportDataRow statRow = statRowMap.get(rowKey);
				if (statRow == null) {
					statRow = new ReportDataRow();
				}
				Object cell = statRow.getCell(rowNode.getName());
				String value = (cell == null) ? "" : cell.toString();
				this.tableRows.append("<td>").append(value).append("</td>");
			}
			this.tableRows.append("</tr>");
			rowIndex++;
		}
		this.tableRows.append("</tbody>");
	}

	@Override
	public void drawTableFooterRows() {
		// TODO Auto-generated method stub
	}
}
