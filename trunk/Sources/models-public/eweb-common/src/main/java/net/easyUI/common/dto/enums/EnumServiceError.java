package net.easyUI.common.dto.enums;

import java.util.LinkedHashMap;


import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ServiceResult 返回时,错误号枚举, Service方法中,对于操作结果中的错误信息通用枚举
 */
public enum EnumServiceError implements EnumBase<Integer> {
	SUCCESS(null, "操作成功") {
		// TODO 取国际化信息
		public String msg(Object... msgs) {
			if (logger.isDebugEnabled()) {
				logger.debug("ServiceErrorEnum out put: ServiceError.success");
			}
			return "ServiceError.success";
		}

		public String msgId() {
			return "ServiceError.success";
		}
	},
	NOT_FIND(1, "没有找到相关数据") {
		public String msg(Object... msgs) {
			if (logger.isDebugEnabled()) {
				logger.debug("ServiceErrorEnum out put: ServiceError.not.find");
			}
			return "ServiceError.not_find";
		}

		public String msgId() {
			return "ServiceError.not_find";
		}
	},
	EXCEPTION(999, "操作中发生异常") {
		public String msg(Object... msgs) {
			if (logger.isDebugEnabled()) {
				logger.debug("ServiceErrorEnum out put: ServiceError.Exception");
			}
			return "ServiceError.exception";
		}

		public String msgId() {
			return "ServiceError.exception";
		}
	},
	FAILED(100, "执行失败") {
		public String msg(Object... msgs) {
			if (logger.isDebugEnabled()) {
				logger.debug("ServiceErrorEnum out put: ServiceError.failed");
			}
			return "ServiceError.failed";
		}

		public String msgId() {
			return "ServiceError.failed";
		}
	},
	NODATA(10, "执行成功,但是无结果") {
		public String msg(Object... msgs) {
			if (logger.isDebugEnabled()) {
				logger.debug("ServiceErrorEnum out put: ServiceError.nodata");
			}
			return "ServiceError.nodata";
		}

		public String msgId() {
			return "ServiceError.nodata";
		}
	},
	SOMESUCCESS(20, "部分成功") {
		public String msg(Object... msgs) {
			if (logger.isDebugEnabled()) {
				logger.debug("ServiceErrorEnum out put: ServiceError.someSuccess");
			}
			return "ServiceError.someSuccess";
		}

		public String msgId() {
			return "ServiceError.someSuccess";
		}
	};

	private final Integer code; // 错误号
	private final String desc; // 错误说明
	private static final Log logger = LogFactory.getLog(EnumServiceError.class);

	EnumServiceError(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 错误号
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * 错误说明
	 */
	public String getDesc() {
		return desc;
	}

	public static EnumServiceError getByCode(String code) {
		return EnumBaseUtils.getByCode(code, values());
	}

	public static EnumServiceError getRequiredByCode(String code)
			throws IllegalArgumentException {
		return EnumBaseUtils.getRequiredByCode(code, values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> toMap() {
		return EnumBaseUtils.toMap(values());
	}

	/**
	 * 默认的错误信息,返回国际化信息
	 */
	public abstract String msg(Object... msgs);

	/**
	 * 默认的错误信息,返回国际化信息KEY
	 */
	public abstract String msgId();
}
