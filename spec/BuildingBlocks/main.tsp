import "../types.tsp";

namespace Resumeholic;

model ResumeBuildingBlock {
  uniqueId: string,
  displayName: string,
  description?: string,
  renderingFormat: string,
  parameters: Record<ResumeBuildingBlockParameter>
}

model ResumeBuildingBlockParameter {
  description?: string,
  type: ResumeBuildingBlockParameterTypes
}

enum ResumeBuildingBlockParameterTypes {
  INT,
  FLOAT,
  STRING,
  BOOLEAN,
}