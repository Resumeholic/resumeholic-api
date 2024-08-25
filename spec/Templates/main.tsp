import "../Documents";
import "../types.tsp";

namespace Resumeholic;

model Template {
  templateId: TemplateId,
  name: string,
  document: Document,
  
  @minValue(0)
  likes: safeint,
  
  tags: string[]
}